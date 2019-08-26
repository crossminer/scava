
export HOST=http://ci4.castalia.camp
export PROJ=modelingepsilon
export DIR_OUT_ADMIN=output_admin/
export DIR_OUT_PROJ=output_projects/$PROJ

mkdir -p $DIR_OUT_ADMIN
mkdir -p $DIR_OUT_PROJ

echo "* Authenticating on API."
curl -i -d '{"username":"admin", "password":"admin"}' \
     -H "Content-Type: application/json" \
     -X POST ${HOST}:8086/api/authentication


echo "* Retrieving list of monitored projects to [administration_projects.txt]."
curl -s ${HOST}:8086/administration/projects/ -o ${DIR_OUT_ADMIN}/administration_projects.txt

echo "* Retrieving list of metrics to [metrics_definition_raw.json]."
curl -s ${HOST}:8182/raw/metrics -o ${DIR_OUT_ADMIN}/metrics_definition_raw.json

echo "* Retrieving list of raw metrics to [metrics_definition.json]."
curl -s ${HOST}:8182/metrics -o ${DIR_OUT_ADMIN}/metrics_definition.json

echo "* Retrieving list of metrics for project $PROJ."
curl -s ${HOST}:8182/projects/p/$PROJ/ -o ${DIR_OUT_PROJ}/project_metrics.json



curl -s ${HOST}:8182/projects/p/$PROJ/m/bugs.averageSentiment
echo ""




