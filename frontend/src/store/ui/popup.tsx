import { create } from "zustand";
import Confirm from "../../components/commons/popup/Confirm";

type State = {
  active: boolean;
  content: React.ReactNode;
};

type Action = {
  openPopup: (component: React.ReactNode) => void;
  closePopup: () => void;
  confirm: (message: string) => Promise<boolean>;
};

export const usePopupStore = create<State & Action>((set) => ({
  active: false,
  content: null,
  openPopup: (component) => {
    set(() => {
      return {
        content: component,
        active: true,
      };
    });
  },
  closePopup: () => {
    set(() => {
      return {
        content: null,
        active: false,
      };
    });
  },
  confirm: (message: string) => {
    const promise = new Promise((resolve, reject) => {
      set(() => {
        return {
          content: <Confirm yes={resolve} no={reject} message={message} />,
          active: true,
        };
      });
    });

    return promise.then(
      () => {
        set(() => {
          return {
            content: null,
            active: false,
          };
        });
        return true;
      },
      () => {
        set(() => {
          return {
            content: null,
            active: false,
          };
        });
        return false;
      }
    );
  },
}));
