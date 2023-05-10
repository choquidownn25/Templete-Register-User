import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './pages/about/about.component';
import { HelpComponent } from './pages/help/help.component';
import { HomeComponent } from './pages/home/home.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ProductoComponent } from './pages/producto/producto.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { PersonaComponent } from './pages/persona/persona.component';
import { EmpleadoComponent } from './pages/empleado/empleado.component';
import { SigninComponent } from './pages/sessions/signin/signin.component';
import { SignupComponent } from './pages/sessions/signup/signup.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'signin',
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
  },
  {
    path: 'about',
    component: AboutComponent,
  },
  {
    path: 'producto',
    component: ProductoComponent,
  },
  {
    path: 'persona',
    component: PersonaComponent,
  },
  {
    path: 'empleado',
    component: EmpleadoComponent,
  },
  {
    path: 'help',
    component: HelpComponent,
  },
  {
    path: 'signin',
    component: SigninComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
