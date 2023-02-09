cp -r client/src/main/java/ entrega-tmp
cp -r server/src/main/java/ entrega-tmp

folder=""
if [ $# -eq 0 ]; then
  folder="entrega.zip"
else
  folder="$1.zip"
fi

zip -r "$folder" entrega-tmp
rm -r entrega-tmp
