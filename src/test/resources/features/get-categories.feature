Feature: GET Catogories

  @Regression
  Scenario: GET Category 6327 with catelogue parameter set to false

    When A get request sent to the API with following parameters:
      | category  | 6327  |
      | catalogue | false |
    Then Response status code is 200
    And Name is "Carbon credits"
    And CanRelist is "true"
    And There is a Promotions element with Name "Gallery"
    And Description of "Gallery" promotions element contains the text "2x larger image"


