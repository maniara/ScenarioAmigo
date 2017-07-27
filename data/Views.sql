CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `training_basicflow_sentence` AS
    SELECT 
        `sentence`.`sentenceNum` AS `sentenceNum`,
        `sentence`.`projectID` AS `projectID`,
        `sentence`.`usecaseID` AS `usecaseID`,
        `sentence`.`sentenceOrder` AS `sentenceOrder`,
        `sentence`.`sentenceContents` AS `sentenceContents`,
        `sentence`.`flowID` AS `flowID`,
        `sentence`.`sentenceType` AS `sentenceType`,
        `sentence`.`sentenceSeq` AS `sentenceSeq`,
        `sentence`.`isRepeatable` AS `isRepeatable`,
        `sentence`.`isOptional` AS `isOptional`,
        `sentence`.`mainVerb` AS `mainVerb`
    FROM
        `sentence`
    WHERE
        (`sentence`.`projectID` IN (SELECT 
                `project`.`projectID`
            FROM
                `project`
            WHERE
                (`project`.`forTraining` = 1))
            AND `sentence`.`flowID` IN (SELECT 
                `flow`.`flowID`
            FROM
                `flow`
            WHERE
                (`flow`.`isAlternative` = 'N')));
                
 CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `training_use_case` AS
    SELECT 
        `usecase`.`usecaseNum` AS `usecaseNum`,
        `usecase`.`usecaseID` AS `usecaseID`,
        `usecase`.`projectID` AS `projectID`,
        `usecase`.`usecaseName` AS `usecaseName`,
        `usecase`.`usecaseDescription` AS `usecaseDescription`,
        `usecase`.`preCondition` AS `preCondition`,
        `usecase`.`postCondition` AS `postCondition`,
        `usecase`.`includedOf` AS `includedOf`,
        `usecase`.`extendsOf` AS `extendsOf`,
        `usecase`.`actor` AS `actor`
    FROM
        `usecase`
    WHERE
        `usecase`.`projectID` IN (SELECT 
                `project`.`projectID`
            FROM
                `project`
            WHERE
                (`project`.`forTraining` = '1'))               
             