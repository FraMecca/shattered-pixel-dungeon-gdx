import sys, socket
import zmq
import time


def main(port):

    context = zmq.Context()
    socket = context.socket(zmq.REP)
    socket.bind("tcp://*:" + str (port))

    while True:
        #  Wait for next request from client
        message = socket.recv()
        print("Received request: %s" % message)

        #  Send reply back to client
        socket.send(b"World")

if __name__ == '__main__':
    import sys
    main(sys.argv[1])
