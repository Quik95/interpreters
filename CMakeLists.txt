cmake_minimum_required(VERSION 3.25)
project(clox C)

add_definitions(${GCC_COVERAGE_COMPILE_FLAGS})

add_compile_options(-Wall -Wextra -Wdouble-promotion)
add_compile_options(-g3)

add_compile_options(-fsanitize=address,undefined -fsanitize-trap=undefined)
add_compile_options(-fsanitize=null)
add_compile_options(-fsanitize=leak)

add_link_options(-fsanitize=address,undefined -fsanitize-trap=undefined)
add_link_options(-fsanitize=null)
add_link_options(-fsanitize=leak)

set(ASAN_OPTIONS=abort_on_error=1:halt_on_error=1:detect_leaks=1)
set(UBSAN_OPTIONS=abort_on_error=1:halt_on_error=1)
set(CMAKE_C_STANDARD 11)

add_executable(clox main.c common.h chunk.h chunk.c memory.h memory.c debug.h debug.c value.h value.c vm.h vm.c compiler.h compiler.c scanner.h scanner.c table.h
        object.h
        object.c
        table.c)
