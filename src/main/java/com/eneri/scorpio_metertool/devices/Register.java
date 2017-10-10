/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eneri.scorpio_metertool.devices;

/**
 *
 * @author Roberto
 */
public enum Register
{
  RANGE_MEASUREMENT_BLOCK(25, 110, "L", "R"),  
  RANGO_DIFERENCIAL_CARGA_PERFIL(128, 4, "L", "R"),  
  NUMERO_LINKER(2, 2, "L", "RW"),  
  NUMERO_CFE(4, 8, "S", "RW"),  
  TARJETA_CONTROL_SERIALNO(12, 5, "S", "R"),  
  ELEMENTO_MEDICION_SERIALNO(17, 5, "S", "RW"),  
  VELOCIDAD_DE_TRANSMISION(22, 1, "L", "RW"),  
  CONTRASENA(23, 2, "L", "RW"),  
  KH(25, 1, "L", "R"),  
  IDPROGRAMATOU(26, 1, "L", "R"),  
  VERSION_FIRMWARE(27, 1, "FW", "R"),  
  TIEMPO_DE_DURACION_MODO_DE_PRUEBA(28, 1, "L", "RW"),  
  TIEMPO_RESTANTE_DE_MODO_DE_PRUEBA(29, 1, "L", "RW"),  
  HORA_DE_RESTABLECIMIENTO_TENSION(30, 2, "DA", "R"),  
  HORA_DE_AUSENCIA_TENSION(32, 2, "DA", "R"),  
  CONTADOR_INTERRUPCIONES(34, 1, "L", "R"),  
  BANDERAS_DEL_SISTEMA(35, 2, "L", "RW"),  
  ENERGIA_ACTIVA_POSITIVA(37, 2, "DO", "R"),  
  ENERGIA_REACTIVA_POSITIVA(39, 2, "DO", "R"),  
  ENERGIA_ACTIVA_NEGATIVA(41, 2, "DO", "R"),  
  ENERGIA_REACTIVA_NEGATIVA(43, 2, "DO", "R"),  
  FECHA(45, 2, "DA", "RW"),  
  FECHA_LOCAL(47, 2, "DA", "R"),  
  FRECUENCIA(49, 2, "DO", "R"),  
  VOLTAJE_FASE_A(51, 2, "DO", "R"),  
  VOLTAJE_FASE_B(53, 2, "DO", "R"),  
  VOLTAJE_FASE_C(55, 2, "DO", "R"),  
  CORRIENTE_FASE_A(57, 2, "DO", "R"),  
  CORRIENTE_FASE_B(59, 2, "DO", "R"),  
  CORRIENTE_FASE_C(61, 2, "DO", "R"),  
  FACTOR_DE_POTENCIA(63, 2, "DO", "R"),  
  FACTOR_DE_POTENCIA_A(65, 2, "DO", "R"),  
  FACTOR_DE_POTENCIA_B(67, 2, "DO", "R"),  
  FACTOR_DE_POTENCIA_C(69, 2, "DO", "R"),  
  DEMANDA_ROLADA_ACTIVA_ENT(71, 2, "DO", "R"),  
  HORA_REG_DEMANDAROL_ACTIVA_ENT(73, 2, "DA", "R"),  
  DEMANDA_ROLADA_REACTIVA_ENT(75, 2, "DO", "R"),  
  HORA_REG_DEMANDAROL_REACTIVA_ENT(77, 2, "DA", "R"),  
  DEMANDA_ROLADA_ACTIVA_RECIB(79, 2, "DO", "R"),  
  HORA_REG_DEMANDAROL_ACTIVA_RECIB(81, 2, "DA", "R"),  
  DEMANDA_ROLADA_REACTIVA_RECIB(83, 2, "DO", "R"),  
  HORA_REG_DEMANDAROL_REACTIVA_RECIB(85, 2, "DA", "R"),  
  POTENCIA_ACTIVA(87, 2, "DO", "R"),  
  POTENCIA_REACTIVA(89, 2, "DO", "R"),  
  POTENCIA_APARENTE(91, 2, "DO", "R"),  
  ENERGIA_ACTIVA_POSITIVA_FASE_A(93, 2, "DO", "R"),  
  ENERGIA_ACTIVA_POSITIVA_FASE_B(95, 2, "DO", "R"),  
  ENERGIA_ACTIVA_POSITIVA_FASE_C(97, 2, "DO", "R"),  
  ENERGIA_REACTIVA_POSITIVA_FASE_A(99, 2, "DO", "R"),  
  ENERGIA_REACTIVA_POSITIVA_FASE_B(101, 2, "DO", "R"),  
  ENERGIA_REACTIVA_POSITIVA_FASE_C(103, 2, "DO", "R"),  
  ENERGIA_ACTIVA_NEGATIVA_FASE_A(105, 2, "DO", "R"),  
  ENERGIA_ACTIVA_NEGATIVA_FASE_B(107, 2, "DO", "R"),  
  ENERGIA_ACTIVA_NEGATIVA_FASE_C(109, 2, "DO", "R"),  
  ENERGIA_REACTIVA_NEGATIVA_FASE_A(111, 2, "DO", "R"),  
  ENERGIA_REACTIVA_NEGATIVA_FASE_B(113, 2, "DO", "R"),  
  ENERGIA_REACTIVA_NEGATIVA_FASE_C(115, 2, "DO", "R"),  
  POTENCIA_ACTIVA_INSTANTANEA_FASE_A(117, 2, "DO", "R"),  
  POTENCIA_ACTIVA_INSTANTANEA_FASE_B(119, 2, "DO", "R"),  
  POTENCIA_ACTIVA_INSTANTANEA_FASE_C(121, 2, "DO", "R"),  
  POTENCIA_REACTIVA_INSTANTANEA_FASE_A(123, 2, "DO", "R"),  
  POTENCIA_REACTIVA_INSTANTANEA_FASE_B(125, 2, "DO", "R"),  
  POTENCIA_REACTIVA_INSTANTANEA_FASE_C(127, 2, "DO", "R"),  
  POTENCIA_APARENTE_INSTANTANEA_FASE_A(129, 2, "DO", "R"),  
  POTENCIA_APARENTE_INSTANTANEA_FASE_B(131, 2, "DO", "R"),  
  POTENCIA_APARENTE_INSTANTANEA_FASE_C(133, 2, "DO", "R"),  
  ADDR_CALIBRATION_CCA(135, 1, "L", "RW"),  
  ADDR_CALIBRATION_CIN(136, 1, "L", "RW"),  
  ADDR_CALIBRATION_CIR(137, 1, "L", "RW"),  
  ADDR_CALIBRATION_CIS(138, 1, "L", "RW"),  
  ADDR_CALIBRATION_CIT(139, 1, "L", "RW"),  
  ADDR_CALIBRATION_CVR(140, 1, "L", "RW"),  
  ADDR_CALIBRATION_CVS(141, 1, "L", "RW"),  
  ADDR_CALIBRATION_CVT(142, 1, "L", "RW"),  
  ADDR_CALIBRATION_CPR(143, 1, "L", "RW"),  
  ADDR_CALIBRATION_CPS(144, 1, "L", "RW"),  
  ADDR_CALIBRATION_CPT(145, 1, "L", "RW"),  
  ADDR_CALIBRATION_CCB(146, 1, "L", "RW"),  
  ADDR_CALIBRATION_CPC(147, 1, "L", "RW"),  
  VOLTAJE_FASE_A_BRUTO(148, 1, "L", "R"), 
  VOLTAJE_FASE_B_BRUTO(149, 1, "L", "R"), 
  VOLTAJE_FASE_C_BRUTO(150, 1, "L", "R"),  
  CORRIENTE_FASE_A_BRUTO(151, 1, "L", "R"),  
  CORRIENTE_FASE_B_BRUTO(152, 1, "L", "R"),  
  CORRIENTE_FASE_C_BRUTO(153, 1, "L", "R"),  
  CORRIENTE_FASE_N_BRUTO(154, 1, "L", "R"),  
  INICIO_HORARIO_VERANO(155, 1, "L", "RW"),  
  FIN_HORARIO_VERANO(156, 1, "L", "RW"),  
  ZONA_HORARIA(157, 1, "L", "RW"),  
  EVENTOS_REGISTRADOS(158, 1, "L", "RW"),  
  PERFIL_DE_CARGA(159, 2, "DO", "RW"),  
  PERFIL_DE_CARGA_DIFF_KWH(161, 2, "DO", "R"),  
  PERFIL_DE_CARGA_DIFF_KVARH(163, 2, "DO", "R"),  
  PERFIL_DE_CARGA_DIFF_FECHA(165, 2, "DA", "R"),  
  INDEX(167, 1, "L", "RW"),  
  ENERGIA_ACTIVA_INDEX(168, 2, "DO", "R"),  
  ENERGIA_REACTIVA_INDEX(170, 2, "DO", "R"),  
  OTHER_REGISTER(0, 0, "HEX", "RW"),  
  CONTRASENABOOT(23, 2, "L", "RW");
  
  private int wordLength;
  private int address;
  private String type;
  private String typeWrite;
  
  private Register(int address, int wordLength, String type, String typeWrite)
  {
    this.address = address;
    this.wordLength = wordLength;
    this.type = type;
    this.typeWrite = typeWrite;
  }
  
  public int getWordLength()
  {
    return this.wordLength;
  }
  
  public int getAddress()
  {
    return this.address;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public String getTypeWrite()
  {
    return this.typeWrite;
  }
}
