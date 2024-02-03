# Script for getting VPS ready to host this project, coexisting with potential future projects

curl -SL https://github.com/docker/compose/releases/download/v2.24.5/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose
sudo apt-get update
sudo apt-get upgrade
# Installed package maintainers version of grub (hopefully this is fine)

sudo apt install zip unzip nginx vsftpd

# Java
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.2-amzn

# nginx
sudo systemctl start nginx
sudo systemctl enable nginx

mkdir /data
mkdir /data/www
