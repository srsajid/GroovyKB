package design.patterns.Iterator;

import design.patterns.Proxy.ProxyImage;

/**
 * Created by sajedur on 2/6/2017.
 */
public class NameRepository implements Container {
    public String names[] = {"Robert" , "John" ,"Julie" , "Lora"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {

        int index;
        public String names[] = {"John" , "Lora"};
        @Override
        public boolean hasNext() {
            if(index < NameRepository.this.names.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {

            if(this.hasNext()){
                return NameRepository.this.names[index++];
            }
            return null;
        }
    }
}