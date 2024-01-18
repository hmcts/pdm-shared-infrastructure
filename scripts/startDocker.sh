cd ..
clear
echo ------ Stopping Colima -------
colima stop
echo ------ Cleanup Colima ------
limactl stop -f colima
echo ------ Starting Colima ------
colima start
echo ------ Cleanup Docker ------
docker-compose down
echo ------ Starting docker build -----
docker-compose build
docker-compose up
echo ------- Finished ------
echo Now goto:  http://localhost:4550/health
