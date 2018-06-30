<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 3:17 PM
 */

class user
{
    private $conn;

    function __construct()
    {
        require_once dirname(__FILE__).'/DBConnector.php';
        $db = new DBConnector();
        $this->conn = $db->connect();
    }

    function register($first_name,$last_name,$email,$password,$gender){
        if(!$this->doesUserExist($email)){
            $pass = password_hash($password,PASSWORD_DEFAULT);
            $stmt = $this->conn->prepare("INSERT INTO users_094781(first_name,last_name,email,password,gender)VALUES(?,?,?,?,?)");
            $stmt->bind_param("sssss",$first_name,$last_name,$email,$pass,$gender);
            if ($stmt->execute())
				return USER_CREATED;
			return USER_CREATION_FAILED;
        }
        return USER_EXIST;
    }

    function login($email, $pass)
    {
		$res = mysqli_query($this->conn,"SELECT * FROM users_094781") or die("Error: ".mysqli_error($conn->conn));

        while ($row=mysqli_fetch_array($res)){
            if(password_verify($pass,$row['password']) && $email==$row['email']){
				$stmt = $this->conn->prepare("SELECT id FROM users_094781 WHERE email = ? AND password = ?");
				$stmt->bind_param("ss", $email, $row['password']);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;
            }
        }
    }

    function getUser($email){
        $stmt = $this->conn->prepare("SELECT id,first_name,last_name,email,gender FROM users_094781 WHERE email = ?");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $stmt->bind_result($id,$first_name,$last_name,$email,$gender);
        $stmt->fetch();
        $user = array();
        $user['id'] = $id;
        $user['first_name'] = $first_name;
        $user['last_name'] = $last_name;
        $user['email'] = $email;
        $user['gender'] = $gender;
        return $user;
    }
    function doesUserExist($email){
        $stmt = $this->conn->prepare("SELECT id FROM users_094781 WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }
}