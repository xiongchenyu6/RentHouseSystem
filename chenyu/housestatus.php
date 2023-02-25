<?php
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    $result = mysql_query("SELECT `light`, `door` FROM `contorl` WHERE `id`=1 ");
    $result = mysql_fetch_array($result);
    $door = $result['door'];
    $light = $result['light'];
    
$link=$door+$light+1;
echo '<'.$link.'>';
?>
