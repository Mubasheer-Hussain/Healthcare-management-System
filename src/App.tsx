import { BrowserRouter, Routes, Route } from "react-router-dom";

import PublicLayout from "@/components/layout/PublicLayout";
import DashboardShell from "@/components/layout/DashboardShell";

import Landing from "@/pages/Landing";
import Login from "@/pages/Login";
import Signup from "@/pages/Signup";
import NotFound from "@/pages/NotFound";

import PatientDashboard from "@/pages/patient/PatientDashboard";
import FindDoctors from "@/pages/patient/FindDoctors";
import MyAppointments from "@/pages/patient/MyAppointments";
import MedicalRecords from "@/pages/patient/MedicalRecords";
import PatientMessages from "@/pages/patient/Messages";

import DoctorDashboard from "@/pages/doctor/DoctorDashboard";
import DoctorAppointments from "@/pages/doctor/DoctorAppointments";
import DoctorPatients from "@/pages/doctor/DoctorPatients";
import DoctorMessages from "@/pages/doctor/DoctorMessages";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<PublicLayout />}>
          <Route index element={<Landing />} />
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<Signup />} />
        </Route>

        <Route path="patient" element={<DashboardShell role="patient" />}>
          <Route index element={<PatientDashboard />} />
          <Route path="find-doctors" element={<FindDoctors />} />
          <Route path="appointments" element={<MyAppointments />} />
          <Route path="records" element={<MedicalRecords />} />
          <Route path="messages" element={<PatientMessages />} />
        </Route>

        <Route path="doctor" element={<DashboardShell role="doctor" />}>
          <Route index element={<DoctorDashboard />} />
          <Route path="appointments" element={<DoctorAppointments />} />
          <Route path="patients" element={<DoctorPatients />} />
          <Route path="messages" element={<DoctorMessages />} />
        </Route>

        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}
