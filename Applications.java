package assignment2;

import java.math.BigInteger;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Applications {

    private PrintStream out;
    private HashMap<Identifier, Set<BigInteger>> map = new HashMap<Identifier,Set<BigInteger>>();

    public Applications(){

        out = new PrintStream(System.out);
        map = new HashMap<Identifier,Set<BigInteger>>();
    }
    
    //========Scanner methods=======//
    boolean nextCharIsLetter(Scanner in) { return in.hasNext("[a-zA-Z]+"); }

    public char nextChar(Scanner in) { return in.next().charAt(0); }

    public boolean nextCharIs(Scanner in, char c) { return in.hasNext(Pattern.quote(""+c)); }

    public boolean nextCharIsAlphanumeric(Scanner in) { return in.hasNext("[a-zA-Z0-9]"); }
    
    public boolean nextCharIsNumber(Scanner in) { return in.hasNext("[0-9]"); }

    boolean multiplicative_operator(Scanner in) { return nextCharIs(in,'*'); }

    boolean additive_operator(Scanner in) {return nextCharIs(in, '+')|| nextCharIs(in, '-')|| nextCharIs(in, '|');}
    
    private void parseWhitespace(Scanner in){
        while (nextCharIs(in, ' ')) {
            nextChar(in);
        }
    }
    
    //====================================//

    //=========Statement methods==========//
    private void assignment(Scanner in) throws APException{
        Identifier identifier = parseIdentifierAssignment(in);

        Set<BigInteger> result = parseExpression(in);

        //ADD identifier and set to HashMap
        if (in.hasNextLine()) {
        	throw new APException("Invalid assignment statement.");
        }
        else map.put(identifier,result);
    }
    
    private void print_statement(Scanner in) throws APException {
        //After '?' we either encounter an identifier or a set followed by an operator

        nextChar(in);//skip '?'
        parseWhitespace(in);
        if (in.hasNext()){
            Set<BigInteger> set = parseExpression(in);
            if(in.hasNext())
            	throw new APException("Invalid print statement");
            print_set(set);
        }else
            throw new APException("Please insert a valid print statement");
    }
    
    private void comment(Scanner in) throws APException {
        nextChar(in);//skip
        parseWhitespace(in);
    }
    
    //=====================================//
    
    //================Term=================//
    
    Set<BigInteger> term(Scanner in) throws APException {
        parseWhitespace(in);

        Set<BigInteger> result = factor(in);

        parseWhitespace(in);

        while (multiplicative_operator(in)) {

            nextChar(in);//skip '*'
            result = (Set<BigInteger>) result.intersection(factor(in));
            parseWhitespace(in);
        }

        return result;
    }
    
    //=====================================//
    
    //=============Factor==================//
    
    Set<BigInteger> factor(Scanner in) throws APException {
        /* factor reads, if possible, a correct factor of the input.
           if this succeeds the factor is evaluated and the resulting
           set is returned. If this fails, then an error-message is given
           and an APException is thrown.
         */
    	//in.useDelimiter("");
    	parseWhitespace(in);
    	
        if (nextCharIsLetter(in)) {
            // read an identifier and retrieve the set that belongs to it
            Identifier identifier = parseIdentifier(in);
            
            Set<BigInteger> set = map.get(identifier);
            if (set == null){
            	throw new APException(identifier.getValue() + " is undefined.");
            }
            else {
            	return set;
            }
        } else
        if(nextCharIs(in, '{')) {
            return  parseSet(in);
        } else
        if (nextCharIs(in, '(')) {
            nextChar(in); //skip '('
            //determine the set that is the result of the complex factor
            Set<BigInteger> result = parseExpression(in);
            parseWhitespace(in);

            if (multiplicative_operator(in)){
                nextChar(in);// skip '*'
                result = result.intersection(parseExpression(in));
            }

            if (additive_operator(in)){
                char operator = nextChar(in);
                result = performOperation(operator, result, parseExpression(in));

            }


            if (!nextCharIs(in, ')')){
                throw new APException("A complex operator was not closed with a ')'");
            }

            nextChar(in);//skip ')'

            return result;

        }

        throw new APException("Error reading in Factor ");
            
    }

    //=====================================//
    
    //============Parse methods============//

    /*--------parse statement--------*/
    private void parse_statement(Scanner in) throws APException{
        parseWhitespace(in);

        if (nextCharIsLetter(in)){
            assignment(in);

        }else if (nextCharIs(in,'?')){
            print_statement(in);

        }else if (nextCharIs(in, '/')) {
            /* do nothing*/
            comment(in);

        } else {
            throw new APException("The input is not a valid statement\n");
        }

    }
    
    /*------------parse Expression------------*/
    private Set<BigInteger> parseExpression(Scanner in) throws APException {
        /*--------read term --------*/
        Set<BigInteger> result = term(in);

        parseWhitespace(in);

        while (this.additive_operator(in)) {
                char operator = nextChar(in);
                result = performOperation(operator, result, term(in));
                parseWhitespace(in);

        }

       parseWhitespace(in);

        return result;
    }
    
    /*--------------parse identifier -----------*/
    private Identifier parseIdentifier(Scanner in) throws APException {
    	StringBuffer sb = new StringBuffer();
        

        while (in.hasNext()) {
            if(nextCharIsAlphanumeric(in)){
                sb.append(nextChar(in));
            }else if (nextCharIs(in, ' ')) {
            	parseWhitespace(in);
            	if (nextCharIsAlphanumeric(in)){
            		throw new APException("Please enter identifier without spaces");
            	}
            } else if (additive_operator(in)||multiplicative_operator(in)||nextCharIs(in,')')){
            	break;
            } else {
                	throw new APException("Identifier consists of only alphanumerical characters");
            }
        }
        
        return new Identifier(sb.toString());
    }

    /*--------------parse identifier for assignment------------*/
    private Identifier parseIdentifierAssignment(Scanner in) throws APException {
        StringBuffer sb = new StringBuffer();
        
        if (nextCharIsLetter(in)) {
        sb.append(nextChar(in));}

        //adding alphanumeric to identifier until we reach '='
        while (in.hasNext()) {
            if(nextCharIsAlphanumeric(in)){
                sb.append(nextChar(in));
            } else
            if (nextCharIs(in, '=')) {
                break;
            }else
            if (nextCharIs(in, ' ')) {
                parseWhitespace(in);
                if (!nextCharIs(in, '=')){
                    throw new APException("Spaces between Identifier are not allowed\n");
                }
            }else {
                throw new APException("Identifier consists of only alphanumerical characters\n");
            }

        }
        if (!in.hasNext()){throw new APException("Please insert a valid statement");}
        //skip '='
        nextChar(in);
        
        return new Identifier(sb.toString());

    }
    
    /*-----------parse Set--------------------*/
    private Set<BigInteger> parseSet(Scanner in) throws APException {
        Set<BigInteger> set;

        nextChar(in);  //skip '{'

        set = rowNaturalNumbers(in);

        parseWhitespace(in);
        if (nextCharIs(in, '}')) {
            nextChar(in);
            return set;
        }

        throw new APException("The elements of a set must be provided between curly brackets.");
    }

    private Set<BigInteger> rowNaturalNumbers(Scanner in) throws APException {
        Set<BigInteger> set = new Set<>();

        parseWhitespace(in);

        /* set may be empty */
        if (!in.hasNext() || !nextCharIsNumber(in)) {
            return set;
        }

        set.addElement(naturalNumber(in));
        parseWhitespace(in);

        while (in.hasNext()) {
            if (!nextCharIs(in, ',')) {
                break;
            }

            nextChar(in); // skip ','
            parseWhitespace(in);

            if (!nextCharIsNumber(in)) {
                throw new APException("There was a non-natural number in the number row");
            }

            set.addElement(naturalNumber(in));
            parseWhitespace(in);
        }

        return set;
    }

    private BigInteger naturalNumber(Scanner in) throws APException {
        StringBuffer numberSB = new StringBuffer();


        while (in.hasNext() && nextCharIsNumber(in)){
            numberSB.append(nextChar(in));
        }

        if (numberSB.length() > 1 && numberSB.charAt(0) == '0') {
            throw new APException("Number started with a 0 but contained non-0 symbols.");
        }

        return new BigInteger(numberSB.toString());
    }

    /*------------print set---------*/
    private void print_set(Set<BigInteger> set) throws APException {

    	Set<BigInteger> copy = set.copy();
    	copy.pointFirstElement();

        while(!copy.isEmpty()){
            out.printf("%s ", copy.getElement().toString());

            copy.removeElement();
        }

        System.out.println();
    }

    /*------------perform additive operation---------*/
    Set<BigInteger> performOperation(char operator, SetInterface<BigInteger> first, SetInterface<BigInteger> second) throws APException {
        if( operator == '+') {
        	
            return (Set<BigInteger>) first.union((Set<BigInteger>) second);
        } else
        if (operator == '-') {
            return (Set<BigInteger>) first.complement((Set<BigInteger>) second);
        } else {
        	
            return (Set<BigInteger>) first.symmetricDifference((Set<BigInteger>) second);}

    }

    //=============Start===============/

    private void start() {
        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()) {
            Scanner line = new Scanner(in.nextLine());
            line.useDelimiter("");
            try {
            	parse_statement(line);
            }catch(APException e) {
            	System.out.println(e.getMessage());
            }
            
        }

    }

    public static void main(String[] argv){
        new Applications().start();
        System.out.println();

    }
}