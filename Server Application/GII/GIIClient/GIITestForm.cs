using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using GII.Data;
using GII.Data.GIIRepositoryPattern;

namespace GIIClient
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void buttonUserInsert_Click(object sender, EventArgs e)
        {
            var user = new User()
            {
                FirstName = textBoxFirstname.Text,
                LastName = textBoxLastname.Text,
                MiddleName = textBoxMiddlename.Text,
                City = textBoxCity.Text,
                Country = textBoxCountry.Text,
                Email = textBoxEmail.Text,
                Password = textBoxPassword.Text,
                NoOfTraveler = Convert.ToInt32(textBoxNoOfTraveler.Text),
                State = textBoxState.Text,
                Street = textBoxStreet.Text,
                ZipCode = Convert.ToInt32(textBoxZipCode.Text)
            };
            using (var db = new GIIContext())
            {
                GIIRepository repo = new GIIRepository(db);                                
                repo.AddUser(user);
                repo.SaveAll();
            } 
        }

        private void buttonQuery_Click(object sender, EventArgs e)
        {
            using (var db = new GIIContext())
            {
                GIIRepository repo = new GIIRepository(db);
                int id = Convert.ToInt32(textBoxUserId.Text);
                User u = repo.GetUserInfo(id);
                if (u != null)
                {
                    MessageBox.Show(u.FirstName+" " + u.MiddleName+ " " + u.LastName);
                };
            } 
        }

        private void buttonReadCoTraveler_Click(object sender, EventArgs e)
        {

        }

        private void buttonCoTravelerInsert_Click(object sender, EventArgs e)
        {
            using (var db = new GIIContext())
            {
                GIIRepository repo = new GIIRepository(db);
                int userId = Convert.ToInt32(textBoxUser_CO_Id.Text);
                CoTraveler co = new CoTraveler()
                {
                    Name = textBoxCoName.Text,
                    Relationship = textBoxCo_Relationship.Text,
                    UserId = Convert.ToInt32(textBoxUser_CO_Id.Text)
                };

                repo.AddCoTraveler(co);
                repo.SaveAll();
            }
        }
    }
}
