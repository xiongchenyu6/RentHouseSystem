<?php
    require_once '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    $username = $_REQUEST['username'];
    $password = $_REQUEST['password'];
    $sex = $_REQUEST['sex'];
    $age = $_REQUEST['age'];
    $hobby = $_REQUEST['hobby'];
    $place = $_REQUEST['place'];
    $type = $_REQUEST['type'];
    $number = $_REQUEST['number'];
    $email = $_REQUEST['email'];

    $query ="INSERT INTO `info`(`username`, `password`, `sex`, `age`, `hobby`, `place`, `type`, `number`, `email`) VALUES('$username','$password','$sex',$age,'$hobby','$place','$type',$number,'$email')";
     echo $query;
    
    if (
    $result = mysql_query($query))
 {
        
        echo 'Success';
        mysql_close();
        
        
    } else {
        echo 'Error Inserting Content';
        exit();
    }
    
?>
