package api.rest;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NetPlayerInst;
import org.zeromq.ZMQ;

import java.io.*;

public class Subscriber extends APIAbstract implements Runnable {

    public static class BEGIN implements Serializable{
        public int pos;
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
                    NetPlayerInst m = NetPlayerInst.spawnImage(HeroClass.MAGE, "9");
                    Dungeon.lanPlayers.add(m);

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
