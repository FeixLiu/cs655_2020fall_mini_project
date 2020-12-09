# Password Cracker

## GENI configuration and any public link: 
- Name of GENI slice: PC-yq
- Project: CS-655-Fall2020
- Rspec file: [rspec.txt](https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/main/rspec.txt)
- Web interface: http://pcvm3-8.instageni.cenic.net/. The port number *58111* can be accessed directly.
- Video demo: https://www.youtube.com/watch?v=O6X7dvg7-DI&t=41s
- Scripts to setup the experiment:
    - For manager node: [ManagerNode.sh](https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/ManagerNode.sh)
    - For worker node: [WorkerNode.sh](https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/WorkerNode.sh)


## How to run / reproduce
- If run on our GENI nodes:
    - Log into all of the nodes (1 manager node and 6 worker nodes) by ssh.
    - Get and run the script on manager node: 
        ```bash
        sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/ManagerNode.sh
        sudo bash ManagerNode.sh
      ```
    - Get and run the script on worker nodes: 
        ```bash
        sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/WorkerNode.sh
        sudo bash WorkerNode.sh
      ```
- If run on your own GENI nodes:
    - Reserve resources by the rspec file: https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/main/rspec.txt 
    - Log into all of the nodes (1 manager node and 6 worker nodes) by ssh.
    - On manager node:
        - Get the script: 
            ```bash
            sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/ManagerNode.sh
          ```
        - In **ManagerNode.sh**:
            - Line 14, change the server name **pcvm3-8.instageni.cenic.net** : <br>
            ` 14: sudo echo '  server_name pcvm3-8.instageni.cenic.net;' >> /etc/nginx/conf.d/static-naice-me.conf `
            - Line 26, change the port number **58888** if necessary: <br>
            ` 26: sudo java Manager 58888 info `
        - Run the script:
            ```bash
            sudo bash ManagerNode.sh
          ```
    - On Worker nodes:
        - Get the script: 
            ```bash
            sudo wget https://raw.githubusercontent.com/FeixLiu/cs655_2020fall_mini_project/password_cracker_v1.0/RunningScripts/WorkerNode.sh
          ```
        - In **WorkerNode.sh**:
            - Line 13, change the port number **58100** if necessary: <br>
            ` 13: sudo java Worker 58100 `
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
        - Run the script:
            ```bash
            sudo bash WorkerNode.sh
          ```
- In the browser, input the server name in **ManagerNode.sh** to access the web page, input the port number in **ManagerNode.sh** to connect to the server and start to test.

## Team members
- Changhao Liang (U16843909)
- Jun Xiao (U85900288)
- Qi Yin (U31787103)
- Yuang Liu (U99473611)
