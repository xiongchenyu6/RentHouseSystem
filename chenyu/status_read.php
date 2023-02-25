<?php
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    $result = mysql_query("SELECT `light`, `door` FROM `contorl` WHERE `id`=1 ");
    $result = mysql_fetch_array($result);
     $user ["door"]  = $result['door'];
    $user ["light"]  = $result['light'];
$response["control"] = array();
 array_push($response["control"], $user);
 echo json_encode($response );
?>