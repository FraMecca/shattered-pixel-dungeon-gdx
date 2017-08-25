package api.rest;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.watabou.utils.Signal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public abstract class APIAbstract {
    private LinkedList<Signal.Listener> listeners;
    private Signal<Hero.ACTION> signal;

    public APIAbstract () {
        this.signal = new Signal<>();
        this.listeners = new LinkedList<>();
    }

    public void add (Signal.Listener el) {
        System.out.println(el);
        this.signal.add(el);
    }

    public void dispatch (Hero.ACTION st, Optional<Integer> n) {
        if (n.isPresent()) {
            signal.valueDispatch(st, n.get());
        } else {
            this.dispatch(st);
        }
    }

    public void dispatch (Hero.ACTION st) {
        signal.dispatch(st);
    }

    public Object returningDispatch (Hero.ACTION st) {return signal.returningDispatch(st, 0);}

}
