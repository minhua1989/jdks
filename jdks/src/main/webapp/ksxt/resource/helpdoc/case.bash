#! usr/bash
case $1 in
       (jpg)
         echo 'It is jpg.'
         ;;
       (jpeg)
         echo 'It is jpeg.'
         ;;
       (png)
         echo "It is png."
       ;;
       (gif)
        echo   "It is gif."
       (*)
         echo "$1 is not an image!"
       ;;
     esac
