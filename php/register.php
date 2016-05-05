<?php
	include_once("connect_database.php");
	
	$phone = "";
	$pass = "";
	$bloodgroup = "";
	$name = "";
	$city = "";
	
	if($_POST)
	{
		$phone=mysql_real_escape_string($_POST['Phone']);
		$pass=mysql_real_escape_string($_POST['Password']);
		$bloodgroup=mysql_real_escape_string($_POST['BloodGroup']);
		$name=mysql_real_escape_string($_POST['Name']);
		$city=mysql_real_escape_string($_POST['City']);
		
		$sql="select count(phone) as num from userinfo where phone='{$phone}';";
		$num=mysql_fetch_array(mysql_query($sql));
		$num=$num['num'];
		
		if($num!=0)
		{
			echo "0#"; // if the user already exists.
			exit();
		}
		
		$sql="insert into userinfo (phone, name, password, bloodgroup, city) values ('{$phone}', '{$name}', password('{$pass}'), '{$bloodgroup}', '{$city}');";
		$inserted=mysql_query($sql);
		
		if($inserted)
			echo "1#";
		else
			echo mysql_error() + "#";
	}
	else echo "3#";
?>
