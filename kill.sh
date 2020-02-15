#!/bin/bash

cd /home/ubuntu/bukkit-server

fuser -k -n tcp 25565
echo "------------------------" >> ./log
echo "Server brutally murdered" >> ./log
echo "------------------------" >> ./log
