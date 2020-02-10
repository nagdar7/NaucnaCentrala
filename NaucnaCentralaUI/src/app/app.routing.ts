import { Authorized } from "./guard/authorized.guard";
import { AllMagazineComponent } from "./magazine/all-magazine.component";
import { Reviewer } from "./guard/reviewer.guard";
import { NewMagazineComponent } from "./magazine/new-magazine.component";
import { LoginComponent } from "./login/login.component";
import { Notauthorized } from "./guard/notauthorized.guard";
import { RegistrationComponent } from "./registration/registration.component";

export const Routing = [
  {
    path: "register",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "magazine",
    component: AllMagazineComponent,
    canActivate: [Authorized]
  },
  {
    path: "magazine/new",
    component: NewMagazineComponent,
    canActivate: [Reviewer]
  }
];
