<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 3:28 PM
 */

class instructor
{
	private $conn;

    function __construct()
    {
        require_once dirname(__FILE__).'/DBConnector.php';
        $db = new DBConnector();
        $this->conn = $db->connect();
    }

    function getAllInstructors(){
        $stmt = $this->conn->prepare("SELECT id, name, phone_number,email, gender FROM instructors_094781");
        $stmt->execute();
        $stmt->bind_result($id, $name,$phone_number, $email,$gender);
        $instructors = array();
        while($stmt->fetch()){
            $temp = array();
            $temp['id'] = $id;
            $temp['name'] = $name;
            $temp['phone_number'] = $phone_number;
            $temp['email'] = $email;
            $temp['gender'] = $gender;
            array_push($instructors, $temp);
        }
        return $instructors;
    }
}