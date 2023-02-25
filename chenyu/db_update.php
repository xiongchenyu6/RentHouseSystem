<?php

    require_once __DIR__ . '/db_connect.php';
 
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
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE `info` SET `username` = '$username', `password` = '$password', `sex` = '$sex',`age`= '$age',`hobby`='$hobby', `place`='$place',`type`='$type'WHERE id = $id");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
