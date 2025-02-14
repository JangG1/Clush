import { create } from "zustand";
import { persist } from "zustand/middleware";

export const useStore = create(
  persist(
    (set, get) => ({
      todos: [], // ✅ 배열로 변경

      // ✅ 할 일 추가
      addTodo: (newTodo) =>
        set((state) => ({ todos: [...state.todos, newTodo] })),

      // ✅ 할 일 삭제
      deleteTodo: (id) =>
        set((state) => ({
          todos: state.todos.filter((todo) => todo.id !== id),
        })),
    }),
    { name: "app-storage" } // ✅ localStorage에 저장됨
  )
);
