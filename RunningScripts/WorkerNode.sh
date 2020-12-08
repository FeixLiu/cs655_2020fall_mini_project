# this is the script for worker node, please run this script before running the script for manager node
sudo apt update
sudo apt install default-jre  # install java
sudo apt install default-jdk  # install javac
sudo rm -rf cs655_2020fall_mini_project-password_cracker_v1.0/  # remove previous project files
sudo rm -f password_cracker_v1.0.tar.gz # remove previous zip files
# download the newest version
sudo wget --no-check-certificate --no-cache --no-cookies https://github.com/FeixLiu/cs655_2020fall_mini_project/archive/password_cracker_v1.0.tar.gz  --post-data="action=purge"
sudo tar -zvxf password_cracker_v1.0.tar.gz # unzip the project
cd cs655_2020fall_mini_project-password_cracker_v1.0/WorkNode  # go into the worker node folder
sudo javac Worker.java # complie the codes
clear # clear the command line
sudo java Worker 58100  # start the worker
