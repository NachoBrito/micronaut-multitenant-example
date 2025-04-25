 INSERT INTO `MERCHANT` (`name`,`uuid`) VALUES ('Merchant 1','afe252cb-3daf-457d-b9e3-e2759e707a0e');
 INSERT INTO `MERCHANT` (`name`,`uuid`) VALUES ('Merchant 2','2b682729-29a9-4b13-a903-53b32590ea05');
 INSERT INTO `CATEGORY` (`name`,`uuid`) VALUES ('Category 1','98ca0372-4169-4241-aab3-1519956071d7');
 INSERT INTO `PRODUCT` (`name`,`uuid`, `category_id`, `merchant_id`) VALUES ('Car','2d557797-a4b3-42e9-a15b-b163fb10c7a4','98ca0372-4169-4241-aab3-1519956071d7','afe252cb-3daf-457d-b9e3-e2759e707a0e');
 INSERT INTO `PRODUCT` (`name`,`uuid`, `category_id`, `merchant_id`) VALUES ('Bicycle','87982202-54bc-41a8-b239-4d7dad5d70fd','98ca0372-4169-4241-aab3-1519956071d7','2b682729-29a9-4b13-a903-53b32590ea05');
 INSERT INTO `USER` (`merchant_id`,`name`,`uuid`) VALUES ('afe252cb-3daf-457d-b9e3-e2759e707a0e','User in Merchant 1','f3d2063e-5c3e-42b7-a3ac-d6ea726c5bba');