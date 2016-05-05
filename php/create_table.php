<?php
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('test.db');
      }
   }
   $connection = mysqli_connect('localhost','username','');
   $db = new MyDB();
   if(!$db){
      echo $db->lastErrorMsg();
   } else {
      echo "Opened database successfully\n";
   }

   $sql =<<<EOF
      CREATE TABLE PROFILE
      (
      PHONE         TEXT PRIMARY KEY    NOT NULL,
      NAME            TEXT     NOT NULL,
      PASSWORD    TEXT     NOT NULL,
      BLOODGROUP     TEXT     NOT NULL,
      CITY        TEXT     NOT NULL);
EOF;

   $ret = $db->exec($sql);
   if(!$ret){
      echo $db->lastErrorMsg();
   } else {
      echo "Table created successfully\n";
   }
   
   $db->close();

?>
