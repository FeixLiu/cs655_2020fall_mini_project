sudo apt update
sudo apt install default-jre
sudo apt install default-jdk
sudo apt install nginx
sudo rm -rf cs655_2020fall_mini_project-password_cracker_v1.0/
sudo rm -f password_cracker_v1.0.tar.gz
sudo wget --no-check-certificate --no-cache --no-cookies https://github.com/FeixLiu/cs655_2020fall_mini_project/archive/password_cracker_v1.0.tar.gz --post-data="action=purge"
sudo tar -zvxf password_cracker_v1.0.tar.gz
sudo touch /etc/nginx/conf.d/static-naice-me.conf
sudo echo 'server {' > /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  server_name pcvm3-8.instageni.cenic.net;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  root '`pwd`'/cs655_2020fall_mini_project-password_cracker_v1.0/FrontEnd;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  index homepage.html;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  location ~* ^.+\.(jpg|jpeg|gif|png|ico|css|js|pdf|txt){' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '    root '`pwd`'/cs655_2020fall_mini_project-password_cracker_v1.0/FrontEnd;' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '  }' >> /etc/nginx/conf.d/static-naice-me.conf
sudo echo '}' >> /etc/nginx/conf.d/static-naice-me.conf
sudo nginx -s reload
cd cs655_2020fall_mini_project-password_cracker_v1.0/ManagerNode
sudo javac Manager.java
clear
sudo java Manager 58888 info
