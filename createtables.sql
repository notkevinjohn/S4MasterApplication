CREATE TABLE IF NOT EXISTS `payloaddata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Payload` varchar(20) NOT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GPS_Lon` double NOT NULL,
  `GPS_Lat` double NOT NULL,
  `GPS_Alt` double NOT NULL,
  `GPS_TimeStamp` varchar(20) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `GPS_Fix` varchar(20) NOT NULL,
  `GPS_Raw` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=848 ;


CREATE TABLE IF NOT EXISTS `sensordata` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PayloadDataID` int(11) NOT NULL,
  `SensorKey` varchar(50) NOT NULL,
  `SensorValue` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=876 ;
