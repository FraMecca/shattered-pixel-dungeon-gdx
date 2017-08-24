package api.rest;

import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.utils.Signal;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class APIAbstract {
    private LinkedList<Signal.Listener> listeners;
    private Signal<String> signal;

    public void add (Signal.Listener el) {
        this.signal.add(el);
    }

    public void dispatch (String st) { signal.dispatch(st);}
}
