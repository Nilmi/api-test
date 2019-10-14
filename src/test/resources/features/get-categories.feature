Feature: GET Catogories

  @Regression
  Scenario Outline: Send GET request to API with parameters "category ID" and "catelogue"

    When A get request sent to the API with parameters "<category>" and "<catalogue>"
    Then Response status code is 200
    And Name is "<category name>"
    And CanRelist is "<CanRelist value>"
    And There is a Promotions element with Name "<promotions name>"
    And Description of "<promotions name>" promotions element contains the "<text>"

    Examples:
      | category | catalogue | category name  | CanRelist value | promotions name | text            |
      | 6327     | false     | Carbon credits | true            | Gallery         | 2x larger image |


