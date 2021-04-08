# -*- coding: utf-8 -*-
import socket
import time
import _thread
import json


def connection(t_index):
    print("线程", t_index, "启动")
    con = socket.create_connection(("121.41.18.173", 58765), 50)
    data = {"option": "online",
            "data": {
                "app_secret": "xxxxxxxxxxxx",
                "invitation_code": "",
                "device_code": "TEST-index" + str(t_index),
                "device_id": "YjQ6ZWY6ZmE6NGM6YmI6Njg2LjA=" + str(t_index)},
            "device_id": "YjQ6ZWY6ZmE6NGM6YmI6Njg2LjA=" + str(t_index)}
    con.send(bytes(json.dumps(data), encoding="utf-8"))
    keep = {"option": "keep_online", "device_id": "YjQ6ZWY6ZmE6NGM6YmI6Njg2LjA=" + str(t_index)}
    while True:
        time.sleep(5)
        con.send(bytes(json.dumps(keep), encoding="utf-8"))
        time.sleep(30)
        print("线程", t_index, ":", con.recv(1024))


def main():
    for i in range(100):
        t = _thread.start_new_thread(connection, (i,))
        time.sleep(1)

    while True:
        time.sleep(100000)


if __name__ == '__main__':
    main()
