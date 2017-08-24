package api.rest;

import com.watabou.utils.Signal;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class APIAbstract {
    private LinkedList<Signal.Listener> listeners;
    private Signal<String> signal;

    public APIAbstract () {
        this.signal = new Signal<>();
        this.listeners = new LinkedList<>();
    }

    public void add (Signal.Listener el) {
        System.out.println(el);
        this.signal.add(el);
    }

    public void dispatch (String st) { System.out.println("Dispatching");signal.dispatch(st);}

}
