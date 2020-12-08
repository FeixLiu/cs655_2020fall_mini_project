# this it the script for the manager node, please run this script after all scripts on worker nodes finished
sudo apt update
sudo apt install default-jre  # install java
sudo apt install default-jdk  # install javac
sudo apt install nginx  #insatll nginx
sudo rm -rf cs655_2020fall_mini_project-password_cracker_v1.0/  # remove previous project files
sudo rm -f password_cracker_v1.0.tar.gz # remove previous zip files
# download the newest version
sudo wget --no-check-certificate --no-cache --no-cookies https://github.com/FeixLiu/cs655_2020fall_mini_project/archive/password_cracker_v1.0.tar.gz --post-data="action=purge"
sudo tar -zvxf password_cracker_v1.0.tar.gz # unzip the project
sudo touch /etc/nginx/conf.d/static-naice-me.conf # create the configuration file for nginx
# start writing configuration for nginx
sudo echo 'server {' > /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  server_name pcvm3-8.instageni.cenic.net;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  root '`pwd`'/cs655_2020fall_mini_project-password_cracker_v1.0/FrontEnd;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  index homepage.html;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  location ~* ^.+\.(jpg|jpeg|gif|png|ico|css|js|pdf|txt){' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '    root '`pwd`'/cs655_2020fall_mini_project-password_cracker_v1.0/FrontEnd;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  }' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '}' >> /etc/nginx/conf.d/static-naice-me.conf
# finish writing configuration for nginx
sudo nginx -s reload  # restart the nginx
cd cs655_2020fall_mini_project-password_cracker_v1.0/ManagerNode  # go into the manager node folder
sudo javac Manager.java # complie the codes
clear # clear the command line
sudo java Manager 58888 info  # start the manager
