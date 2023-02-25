<?php
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    $result = mysql_query("SELECT `pageValue`, `time` FROM `status` ORDER BY `time` DESC LIMIT 7");
if (mysql_num_rows($result) > 0) {
    $response["users"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
            $user = array();
            $user["tempreture"] = $row["pageValue"];                
            $user["time"] = $row["time"];
        // push single product into final response array
        array_push($response["users"], $user);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode(($response))
;
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>