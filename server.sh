#!/usr/bin/env bash
function switchFile {
position=`expr index "$1" $" "`
request=$1;
if [ ${request:0:$position} = $"PORT" ] || [ ${request:0:$position} = $"LOCATION" ] || [ ${request:0:$position} = $"APP_BASE" ]
then
changeConst "$1" 'server.properties'
fi
if [ ${request:0:$position} = $"NAME_DATA_BASE" ] || [ ${request:0:$position} = $"COLLECTION_NAME" ] || [ ${request:0:$position} = $"FIELD_KEY" ]
then
changeConst "$1" 'data_base.properties'
fi
if [ ${request:0:$position} = $"RATES_REQUEST" ]
then
changeConst "$1" 'http_exchange_rates.properties'
fi
if [ ${request:0:$position} = $"YANDEX_KEY" ] || [ ${request:0:$position} = $"GOOGLE_KEY" ] || [ ${request:0:$position} = $"REQUEST_GEOCORDINATING" ] || [ ${request:0:$position} = $"YANDEX_WEATHER_REQUEST" ]
then
changeConst "$1" 'http_temperature.properties'
fi
}

function changeConst {
file=$2
request=$1
len=$[`expr index "$1" $" "` - 1]
key=${request:0:len}

cd ./src/main/resources
out=''

for line in $(cat $file)
do
if [ "${line:0:len}" = "$key" ]
then
	if [ "$out" = $"" ]
	then
	out=$out${choice:0:len}"="${choice:$[$len+1]}
	else
	out=$out"\n"${choice:0:len}"="${choice:$[$len+1]}
	fi
else
if [ "$out" = $"" ]
then
out=$out$line
else
out=$out"\n"$line
fi
fi
done <$file
echo -e "$out">$file
cd -
}
function showAll {
cd ./src/main/resources/
echo "server.properties:"
cat -n server.properties 
echo -e "\n"
echo "data_base.properties:"
cat -n data_base.properties 
echo -e "\n"
echo "http_exchange_rates.properties:"
cat -n http_exchange_rates.properties 
echo -e "\n"
echo "http_temperature.properties:"
cat -n http_temperature.properties
cd -
}

echo -e "Введите help,чтобы вызвать справку"
choice=''
while true
do
echo -n ">"
read choice
if [ "$choice" = $"run" ]
then
if [ -d build ]
then
rm -r build
fi
gradle build
java -jar ./build/libs/TestTaskNew-1.0-SNAPSHOT-all.jar
fi
if [ ${choice:0:3} = $"set" ]
then
choice=${choice:4}
switchFile "$choice"
fi
if [ ${choice:0:4} = $"help" ]
then
choice=${choice:5}
	if [ "$choice" = $"" ]
	then
	echo -e "Введите run - чтобы начать сборку \nВведите set имя_параметра значение - чтобы установить значение константы \nВведите help const - чтобы просмотреть список доступных констант\nstop - выйти из скрипта"
	fi
	if [ "$choice" = $"const" ]
	then
	echo -e "PORT - номер порта \nAPP_BASE - директория приложения\nLOCATION - расположение верхней директории приложения\nYANDEX_KEY - ключ для яндекс погоды\nGOOGLE_KEY - ключ для определения местоположения\nREQUEST_GEOCORDINATING - запрос для определения местоположения\nYANDEX_WEATHER_REQUEST - запрос для погоды от яндекса\nRATES_REQUEST - запрос для определения валют\nNAME_DATA_BASE - имя базы данных\nCOLLECTION_NAME -имя коллекции\nFIELD_KEY - имя ключа"
	fi
fi
if [ "$choice" = $"stop" ]
then
exit 0
fi
if [ "$choice" = $"show" ]
then
showAll
fi
done
