package api.rest;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NetPlayerInst;
import org.zeromq.ZMQ;

import java.io.*;
import java.util.HashSet;

public class Subscriber extends APIAbstract implements Runnable {

    public static class BEGIN implements Serializable{
        public int pos;
        public HeroClass cl;
        public Integer id;

        public BEGIN(Hero hero) {
            pos = hero.pos;
            cl = hero.heroClass;
            id = hero.lanId;
        }
    }


    final ZMQ.Context context = ZMQ.context(1);
    final ZMQ.Socket client = context.socket(ZMQ.SUB);
    static private Subscriber publicSub = null;

    static public synchronized Subscriber getSubscriber () {
        return publicSub;
    }

    public Subscriber(Integer port) {
        boolean fl = false;
        while (!fl) {
            fl = client.connect("tcp://localhost:" + port.toString());
            client.subscribe("".getBytes());
            publicSub = this;
        }
    }

    public void recv() {
        try {
            while (true) {
                byte[] rec = client.recv();
                System.out.println(rec.length);
                ByteArrayInputStream in = new ByteArrayInputStream(rec);
                ObjectInputStream is = new ObjectInputStream(in);
                Object recvdObj = is.readObject();
                if (recvdObj instanceof BEGIN) {
                    boolean alreadyAdded = false;
                    for (Object m : Dungeon.lanPlayers) {
                        if (((NetPlayerInst)m).lanId.equals (((BEGIN) recvdObj).id)) {
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if (!alreadyAdded) {
                        NetPlayerInst m = NetPlayerInst.spawnImage(((BEGIN) recvdObj).cl, ((BEGIN) recvdObj).id);
                        Dungeon.lanPlayers.add(m);
                        System.out.println("Created netinsta: " + m.lanId);
                    }

                } else {
                    this.dispatch(recvdObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       this.recv();
    }


}
