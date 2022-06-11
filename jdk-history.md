## Основные изменения в Java 8

1. Лямбда-выражения и Stream API
2. Optional контейнер
3. Ссылка на метод
4. Методы по умолчанию
5. Аннотации типов

## Основные изменения в Java 9

1. Разрешено использовать final переменные в качестве ресурсов в операторе try-with-resources

      
         try (final FileInputStream fileInputStream = new FileInputStream("file.txt")){
            // code
         } catch (IOException e) {
            e.printStackTrace();
         }

2. Private методы в интерфейсах


         public interface SomeInterface {

             private static void staticVoid(){
                 // вспомогательный static метод
             }

             private void someVoid() {
                 // вспомогательный метод, его можно
                 // вызвать в default методе
             }
         }


3. Система модулей Java

4. Diamond оператор для анонимных внутренних классов


         public interface SomeInterface<T> {
             void someVoid1(T t);
             T someVoid2();
         }


         SomeInterface<String> someInterface = new SomeInterface<>() {
            @Override
            public void someVoid1(String s) {
                // реализация метода
            }

            @Override
            public String someVoid2() {
                // реализация метода
                return null;
            }
        }; 

   

## Основные изменения в Java 10

Локальная переменная (var) позволяет не указывать тип данных
   

      var unit1 = new Unit(223, 145);
      var unit2 = new Unit(332, 98);
   
      var army = List.of(unit1, unit2);


## Основные изменения в Java 11

Локальная переменная (var) позволяет не указывать тип данных внутри лямбда-выражений


## Основные изменения в Java 14

1. Switch выражения


       String season = "";
       int month = 2;

        season = switch (month) {
            case 12, 1, 2 -> "Зима";
            case 3, 4, 5 -> "Весна";
            case 6, 7, 8 -> "Лето";
            case 9, 10, 11 -> "Осень";
            default -> "Ошибка ввода";
        };

2. Switch выражения и ключевое слово yield


        String season = "";
        int month = 2;

        season = switch (month) {
            case 12, 1, 2 -> {
                System.out.println(month);
                yield "Зима";
            }
            case 3, 4, 5 -> {
                System.out.println(month);
                yield "Весна";
            }
            case 6, 7, 8 -> {
                System.out.println(month);
                yield "Лето";
            }
            case 9, 10, 11 -> {
               System.out.println(month);
               yield "Осень";
            }
            default -> "Ошибка ввода";
        };


## Основные изменения в Java 15

1. Текстовые блоки `""" """`
2. instanceof с приведением


      до 15 версии:
      if (v instanceof Bus) {
         Bus bus = (Bus) v;
         System.out.println(bus.getNum());
      } 


      начиная с 15 версии:
      if (v instanceof Bus bus) {
         System.out.println(bus.getNum());
      } 
