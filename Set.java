package assignment2;

public class Set<E extends Comparable<E>> implements SetInterface<E> {

    List<E> list = new List<>();

    public Set() {init();}

    @Override
    public Set<E> init() {
        list.init();
        return this;
    }

    //Return number of elements in the set.
    @Override
    public int numberOfElements() {
        return list.size();
    }

    @Override
    public boolean contains(E element) {
        return list.find(element);
    }

    //Go to the first element in the set.
    public void pointFirstElement(){
        list.goToFirst();
    }

    public Set<E> copy() {
        Set<E> copySet = new Set<E>();
        list.goToFirst();
        if(!list.isEmpty())
            copySet.addElement(list.retrieve());
        while (list.goToNext()){
            copySet.addElement(list.retrieve());
        }
        return copySet;
    }

    @Override
    public void addElement(E element) {if(!list.find(element))list.insert(element);}

    @Override
    public E getElement() { return list.retrieve();}

    @Override
    public void removeElement() throws APException {list.remove();}

    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    @Override
    public Set<E> complement(Set<E> set) {

        Set<E> copySet = set.copy();

        Set<E> complementSet = this.copy();

        while (!copySet.isEmpty()){
            if(complementSet.contains(copySet.getElement())){
                try {
                    complementSet.removeElement();
                } catch (APException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                copySet.removeElement();
            } catch (APException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return complementSet;
    }

    @Override
    public Set<E> intersection(Set<E> set) {
        Set<E> copySet = this.copy();

        Set<E> intersectionSet = new Set<E>();

        while (!copySet.isEmpty()){
            if (set.contains(copySet.getElement())){
                intersectionSet.addElement(copySet.getElement());
            }
            try {
                copySet.removeElement();
            } catch (APException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return intersectionSet;
    }

    @Override
    public Set<E> union(Set<E> set) throws APException {

        Set<E> result = set.copy();
        list.goToFirst();
        if(!list.isEmpty()) result.addElement(list.retrieve());
        while(list.goToNext())
            result.addElement(list.retrieve());

        return result;
    }

    @Override
    public Set<E> symmetricDifference(Set<E> set) throws APException {
        Set<E> unionSet = union(set);
        Set<E> intersectionSet = intersection(set);

        Set<E> symmetricDifferenceSet= unionSet.complement(intersectionSet);

        return symmetricDifferenceSet;
    }


}

