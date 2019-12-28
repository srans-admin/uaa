## Deployment through Docker for UAA App on AWS EC2 Instance
## Author : Ram
## DevOps: Krishna

##--no-cache
git checkout -f $1
git pull 
docker build -t uaa .
docker stop uaa-container
docker rm uaa-container
docker run -p 9090:9090 -d -it  --name uaa-container uaa