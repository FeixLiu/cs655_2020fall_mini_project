# Password Cracker

## GENI configuration and any public link: 
- Name of GENI slice: PC-yq
- Project: CS-655-Fall2020
- Rspec file: https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/main/rspec.txt 
- Web interface: http://pcvm3-8.instageni.cenic.net/. The port number *58111* can be accessed directly.
- Video demo: https://www.youtube.com/watch?v=O6X7dvg7-DI&t=41s


## How to run
- Get and run the script on manager node: 
    <br>```sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/ManagerNode.sh```
    <br>```sudo bash ManagerNode.sh```
- Get and run the script on worker nodes: 
    <br>```sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/WorkerNode.sh```
    <br>```sudo bash WorkerNode.sh```
- If run on your own GENI nodes, before run the scripts:
    - In **ManagerNode.sh**
        - Line 11, change the server name **pcvm3-8.instageni.cenic.net** : <br>
        ` sudo echo '  server_name pcvm3-8.instageni.cenic.net;' >> /etc/nginx/conf.d/static-naice-me.conf `
        - Line 22, change the port number **58888** if necessary: <br>
        ` sudo java Manager 58888 info `
    - In **WorkerNode.sh**
        - Line 11, change the port number **58100** if necessary: <br>
        ` sudo java Worker 58100 `
    - In **cs655_2020fall_mini_project-password_cracker_v1.0/ManagerNode/info**, change the configuration if necessary: 
        - Line 1, the number of workers
        - Line 2, the port number in **WorkerNode.sh**
        - The number of following lines are equal to the number of workers, the first ip is the ip address on manager node, and the second ip is the ip address on related worker node
        ```
          6
          58100
          10.10.1.2 10.10.1.1
          10.10.2.2 10.10.2.1
          10.10.3.2 10.10.3.1
          10.10.4.2 10.10.4.1
          10.10.5.2 10.10.5.1
          10.10.6.2 10.10.6.1
        ```
    - On the web page, input the port number in the **ManagerNode.sh**.

## Team members
- Changhao Liang (U16843909)
- Jun Xiao (U85900288)
- Qi Yin (U31787103)
- Yuang Liu (U99473611)
