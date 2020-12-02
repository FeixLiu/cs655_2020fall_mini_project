sudo apt update
sudo apt install default-jre
sudo apt install default-jdk
sudo apt install nginx
sudo wget https://github.com/FeixLiu/cs655_2020fall_mini_project/archive/password_cracker_v1.0.tar.gz
sudo tar -zvxf password_cracker_v1.0.tar.gz
cd cs655_2020fall_mini_project-password_cracker_v1.0/WorkNode
sudo javac Main.java
sudo java Main 58100
