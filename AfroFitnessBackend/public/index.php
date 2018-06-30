<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 4:24 PM
 */
use \Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';
require_once '../includes/user.php';

$app = new \Slim\App([
   'settings' => [
       'displayErrorDetails' => true
   ]
]);

$app->post('/register',function(Request $request,Response $response){
    if(areTheseParametersAvailable(array('first_name','last_name','email','password','gender'))){
        $requestData = $request->getParsedBody();
        $first_name = $requestData['first_name'];
        $last_name = $requestData['last_name'];
        $email = $requestData['email'];
        $password = $requestData['password'];
        $gender = $requestData['gender'];
        $db = new user();
        $responseData = array();

        $result = $db->register($first_name,$last_name,$email,$password,$gender);

        if($result == USER_CREATED){
            $responseData['error'] = false;
            $responseData['message'] = 'Registered Successfully';
            $responseData['user'] = $db->getUser($email);
        }
        elseif ($result == USER_CREATION_FAILED){
            $responseData['error'] = true;
            $responseData['message'] = 'An error occured';
        }
        elseif ($result==USER_EXIST){
            $responseData['error'] = true;
            $responseData['message'] = 'This email is already in use';
        }

        $response->getBody()->write(json_encode($responseData));
    }
});

$app->post('/login',function(Request $request,Response $response){
    if(areTheseParametersAvailable(array('email','password'))){
        $requestData = $request->getParsedBody();
        $email = $requestData['email'];
        $password = $requestData['password'];

        $db = new user();

        $responseData = array();

        if($db->login($email,$password)){
            $responseData['error'] = false;
            $responseData['user'] = $db->getUser($email);
        }
        else{
            $responseData['error'] = true;
            $responseData['message'] = 'Invalid email or password';
        }
        $response->getBody()->write(json_encode($responseData));
    }
});

function areTheseParametersAvailable($required_fields){
    $error = false;
    $error_fields = "";
    $request_params = $_REQUEST;

    foreach ($required_fields as $field){
        if(!isset($request_params[$field])||strlen(trim($request_params[$field]))<=0){
            $error = true;
            $error_fields.=$field.', ';
        }
    }

    if($error){
        $response = array();
        $response["error"] = true;
        $response["message"] = 'Required fields(s)'.substr($error_fields,0,-2).'is missing or empty';
        echo json_encode($response);
        return false;
    }
    return true;
}
$app->run();