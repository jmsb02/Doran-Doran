import { create } from "zustand";

type Toast = { id: number; message: string };

type State = {
  list: Toast[];
};

type Action = {
  addToast: (message: string) => void;
  removeToast: (id: number) => void;
};

export const useToastStore = create<State & Action>((set) => ({
  list: [],
  addToast: (message) => {
    set((state) => {
      return {
        list: [...state.list, { id: Date.now(), message: message }],
      };
    });
    setTimeout(() => {
      set((state) => {
        return {
          list: state.list.filter((e) => state.list.indexOf(e) !== 0),
        };
      });
    }, 5000);
  },
  removeToast: (id: number) => {
    set((state) => {
      return {
        list: state.list.filter((e) => e.id !== id),
      };
    });
  },
}));
