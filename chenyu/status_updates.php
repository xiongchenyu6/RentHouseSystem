<?php
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $door = $_REQUEST['door'];
    $light = $_REQUEST['light'];
    
    $query ="UPDATE `contorl` SET `door` = '$door', `light` = '$light' WHERE id = 1";
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