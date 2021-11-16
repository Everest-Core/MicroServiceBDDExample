Feature: El usuario debe consultar y actualizar la información personal del cliente
  
  Scenario: Consultar la información del cliente
    Given que requiero consultar la información de todos los clientes
    When ejecute la acción de obtener los clientes
    Then se debe devolver un estado ok
    And se debe proporcionar la información de todos los clientes

  Scenario Outline: Consultar información de cliente por Id
    Given dado que requiero consultar la información del cliente
    When ejecute la accion de consultar por Id <id>
    Then se debe obtener un estado ok
    And debe proporcionarse el "<nombre>", "<apellidos>", "<sexo>" del cliente con Id <id>
    Examples:
      | id  | nombre    | apellidos    | sexo |
      | 1   | Christian | Meneses      | M    |
      | 2   | Juan      | Salgado      | M    |
      | 3   | Sandra    | Martinez     | F    |

  Scenario Outline: Guardar información del cliente
    Given que requiero dar de alta un cliente con el "<nombre>", "<apellidos>" y "<sexo>"
    When ejecute la acción de guardar
    Then se debe devolver un estado creado
    And el "<nombre>", "<apellidos>" y "<sexo>" debió ser guardado en la Base de Datos
    Examples:
      | nombre   | apellidos | sexo |
      | Ariana   | Gutierres | M    |
      | Julian   | Mena      | M    |
      | Veronica | Martinez  | F    |

  Scenario Outline: Actualizar información de clientes
    Given que requiero actualizar el "<nombre>", "<apellidos>", "<sexo>" de los clientes con Id <id>
    When ejecute la acción de actualizar
    Then se debe devolver un estado aceptado
    And el "<nombre>", "<apellidos>" y "<sexo>" de los clientes con Id <id> debió ser guardado en la Base de Datos
    Examples:
      | nombre       | apellidos          | sexo  | id  |
      | Ariana       | Gutierres Zavaleta | M     | 1   |
      | Julian Omar  | Mena               | M     | 2   |
      | Veronica     | Martinez Hernandez | F     | 3   |

  Scenario Outline: Eliminar cliente
    Given dado que requiero eliminar clientes
    When ejecute la accion de eliminar por el Id <id>
    Then se debe devolver un estado de no contenido
    Examples:
      | id  |
      | 1   |
      | 3   |