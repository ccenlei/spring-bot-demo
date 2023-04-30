CREATE TABLE `mint_fun_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'user name',
  `addr` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT 'user eth address',
  `following` int NOT NULL DEFAULT '0' COMMENT 'user following count',
  `followers` int NOT NULL DEFAULT '0' COMMENT 'user followers count',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='Mint fun user info';

insert into mint_fun_user (name, addr, following, followers) values ("sjaskillz", "0x4306dd0c18b58e34e587003841d736a449392d49", 118, 65);
insert into mint_fun_user (name, addr, following, followers) values ("xpub.eth", "xpub.eth", 22, 0);
insert into mint_fun_user (name, addr, following, followers) values ("badjob.eth", "badjob.eth", 9, 0);

---------------------------------------------------------------------------------------

CREATE TABLE `mint_fun_nft` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'nft name',
  `token_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'nft token id',
  `addr` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT 'nft contract address',
  `creator` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'nft creator name',
  `total` int NOT NULL COMMENT 'nft total count',
  `owner_id` int NOT NULL COMMENT 'nft owner id',
  PRIMARY KEY (`id`),
  KEY `owner_index` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='Mint fun nft info';

insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("PsychoNauts", "#2676", "0xe05590833120f8a671d43aa3e9870fed9361b4ca", "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0", 7777, 3);
insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("PsychoNauts", "#2675", "0xe05590833120f8a671d43aa3e9870fed9361b4ca", "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0", 7777, 3); 
insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("PsychoNauts", "#2674", "0xe05590833120f8a671d43aa3e9870fed9361b4ca", "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0", 7777, 3); 
insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("PsychoNauts", "#3655", "0xe05590833120f8a671d43aa3e9870fed9361b4ca", "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0", 7777, 2); 
insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("PsychoNauts", "#3654", "0xe05590833120f8a671d43aa3e9870fed9361b4ca", "0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0", 7777, 2); 
insert info mint_fun_nft (name, token_id, addr, creator, total, owner) values 
                         ("Dot Dot Punks", "#170", "0x9b5ac0fba34e43f92b95e9160ad68a91f1657d3d", "0xdda4e7fc052913548a68bad6581710ef9dc21419", 307, 1); 

         
