#language: es

Característica: Demostración de esperas implícitas con carga dinámica de elementos
  Como estudiante de automatización
  Quiero demostrar cómo funcionan las esperas implícitas
  Para entender que solo esperan a que elementos EXISTAN en el DOM

  Escenario: Espera implícita al buscar elemento que se agrega al DOM después de un delay
    Dado que el usuario abre la pagina de carga dinamica
    Cuando el usuario hace clic en el boton de inicio
    Entonces debe aparecer el mensaje "Hello World!"

  # NOTA EDUCATIVA:
  # Este escenario demuestra esperas implícitas porque:
  # 1. El mensaje "Hello World!" NO existe en el DOM al inicio
  # 2. Solo después de ~5 segundos se AGREGA al DOM
  # 3. Sin esperas implícitas: NoSuchElementException inmediata
  # 4. Con esperas implícitas: Selenium espera hasta 10 segundos a que aparezca en el DOM