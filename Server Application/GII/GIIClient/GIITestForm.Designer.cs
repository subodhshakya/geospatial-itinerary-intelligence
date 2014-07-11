namespace GIIClient
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tabControlTest = new System.Windows.Forms.TabControl();
            this.User = new System.Windows.Forms.TabPage();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.buttonReadCoTraveler = new System.Windows.Forms.Button();
            this.buttonQuery = new System.Windows.Forms.Button();
            this.textBoxUserId = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.textBoxZipCode = new System.Windows.Forms.TextBox();
            this.textBoxStreet = new System.Windows.Forms.TextBox();
            this.textBoxState = new System.Windows.Forms.TextBox();
            this.textBoxNoOfTraveler = new System.Windows.Forms.TextBox();
            this.textBoxPassword = new System.Windows.Forms.TextBox();
            this.textBoxEmail = new System.Windows.Forms.TextBox();
            this.textBoxCountry = new System.Windows.Forms.TextBox();
            this.textBoxCity = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.textBoxLastname = new System.Windows.Forms.TextBox();
            this.textBoxMiddlename = new System.Windows.Forms.TextBox();
            this.textBoxFirstname = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.buttonUserInsert = new System.Windows.Forms.Button();
            this.Sector = new System.Windows.Forms.TabPage();
            this.tabPageCoTraveler = new System.Windows.Forms.TabPage();
            this.buttonCoTravelerInsert = new System.Windows.Forms.Button();
            this.textBoxCo_Relationship = new System.Windows.Forms.TextBox();
            this.textBoxCoName = new System.Windows.Forms.TextBox();
            this.textBoxUser_CO_Id = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.button1 = new System.Windows.Forms.Button();
            this.tabControlTest.SuspendLayout();
            this.User.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.tabPageCoTraveler.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabControlTest
            // 
            this.tabControlTest.Controls.Add(this.User);
            this.tabControlTest.Controls.Add(this.Sector);
            this.tabControlTest.Controls.Add(this.tabPageCoTraveler);
            this.tabControlTest.Controls.Add(this.tabPage1);
            this.tabControlTest.Location = new System.Drawing.Point(12, 12);
            this.tabControlTest.Name = "tabControlTest";
            this.tabControlTest.SelectedIndex = 0;
            this.tabControlTest.Size = new System.Drawing.Size(600, 418);
            this.tabControlTest.TabIndex = 0;
            // 
            // User
            // 
            this.User.Controls.Add(this.groupBox1);
            this.User.Controls.Add(this.textBoxZipCode);
            this.User.Controls.Add(this.textBoxStreet);
            this.User.Controls.Add(this.textBoxState);
            this.User.Controls.Add(this.textBoxNoOfTraveler);
            this.User.Controls.Add(this.textBoxPassword);
            this.User.Controls.Add(this.textBoxEmail);
            this.User.Controls.Add(this.textBoxCountry);
            this.User.Controls.Add(this.textBoxCity);
            this.User.Controls.Add(this.label10);
            this.User.Controls.Add(this.label11);
            this.User.Controls.Add(this.label7);
            this.User.Controls.Add(this.label8);
            this.User.Controls.Add(this.label9);
            this.User.Controls.Add(this.label4);
            this.User.Controls.Add(this.label5);
            this.User.Controls.Add(this.label6);
            this.User.Controls.Add(this.textBoxLastname);
            this.User.Controls.Add(this.textBoxMiddlename);
            this.User.Controls.Add(this.textBoxFirstname);
            this.User.Controls.Add(this.label3);
            this.User.Controls.Add(this.label2);
            this.User.Controls.Add(this.label1);
            this.User.Controls.Add(this.buttonUserInsert);
            this.User.Location = new System.Drawing.Point(4, 22);
            this.User.Name = "User";
            this.User.Padding = new System.Windows.Forms.Padding(3);
            this.User.Size = new System.Drawing.Size(592, 392);
            this.User.TabIndex = 0;
            this.User.Text = "User";
            this.User.UseVisualStyleBackColor = true;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.buttonReadCoTraveler);
            this.groupBox1.Controls.Add(this.buttonQuery);
            this.groupBox1.Controls.Add(this.textBoxUserId);
            this.groupBox1.Controls.Add(this.label12);
            this.groupBox1.Location = new System.Drawing.Point(6, 289);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(580, 100);
            this.groupBox1.TabIndex = 23;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Get User Info";
            // 
            // buttonReadCoTraveler
            // 
            this.buttonReadCoTraveler.Location = new System.Drawing.Point(375, 16);
            this.buttonReadCoTraveler.Name = "buttonReadCoTraveler";
            this.buttonReadCoTraveler.Size = new System.Drawing.Size(75, 44);
            this.buttonReadCoTraveler.TabIndex = 7;
            this.buttonReadCoTraveler.Text = "Read CoTraveler";
            this.buttonReadCoTraveler.UseVisualStyleBackColor = true;
            this.buttonReadCoTraveler.Click += new System.EventHandler(this.buttonReadCoTraveler_Click);
            // 
            // buttonQuery
            // 
            this.buttonQuery.Location = new System.Drawing.Point(294, 16);
            this.buttonQuery.Name = "buttonQuery";
            this.buttonQuery.Size = new System.Drawing.Size(75, 44);
            this.buttonQuery.TabIndex = 6;
            this.buttonQuery.Text = "Read";
            this.buttonQuery.UseVisualStyleBackColor = true;
            this.buttonQuery.Click += new System.EventHandler(this.buttonQuery_Click);
            // 
            // textBoxUserId
            // 
            this.textBoxUserId.Location = new System.Drawing.Point(98, 16);
            this.textBoxUserId.Name = "textBoxUserId";
            this.textBoxUserId.Size = new System.Drawing.Size(190, 20);
            this.textBoxUserId.TabIndex = 5;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(22, 19);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(41, 13);
            this.label12.TabIndex = 0;
            this.label12.Text = "User Id";
            // 
            // textBoxZipCode
            // 
            this.textBoxZipCode.Location = new System.Drawing.Point(104, 263);
            this.textBoxZipCode.Name = "textBoxZipCode";
            this.textBoxZipCode.Size = new System.Drawing.Size(190, 20);
            this.textBoxZipCode.TabIndex = 22;
            // 
            // textBoxStreet
            // 
            this.textBoxStreet.Location = new System.Drawing.Point(104, 239);
            this.textBoxStreet.Name = "textBoxStreet";
            this.textBoxStreet.Size = new System.Drawing.Size(190, 20);
            this.textBoxStreet.TabIndex = 21;
            // 
            // textBoxState
            // 
            this.textBoxState.Location = new System.Drawing.Point(104, 215);
            this.textBoxState.Name = "textBoxState";
            this.textBoxState.Size = new System.Drawing.Size(190, 20);
            this.textBoxState.TabIndex = 20;
            // 
            // textBoxNoOfTraveler
            // 
            this.textBoxNoOfTraveler.Location = new System.Drawing.Point(104, 191);
            this.textBoxNoOfTraveler.Name = "textBoxNoOfTraveler";
            this.textBoxNoOfTraveler.Size = new System.Drawing.Size(190, 20);
            this.textBoxNoOfTraveler.TabIndex = 19;
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.Location = new System.Drawing.Point(104, 167);
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.Size = new System.Drawing.Size(190, 20);
            this.textBoxPassword.TabIndex = 18;
            // 
            // textBoxEmail
            // 
            this.textBoxEmail.Location = new System.Drawing.Point(104, 143);
            this.textBoxEmail.Name = "textBoxEmail";
            this.textBoxEmail.Size = new System.Drawing.Size(190, 20);
            this.textBoxEmail.TabIndex = 17;
            // 
            // textBoxCountry
            // 
            this.textBoxCountry.Location = new System.Drawing.Point(104, 119);
            this.textBoxCountry.Name = "textBoxCountry";
            this.textBoxCountry.Size = new System.Drawing.Size(190, 20);
            this.textBoxCountry.TabIndex = 16;
            // 
            // textBoxCity
            // 
            this.textBoxCity.Location = new System.Drawing.Point(104, 95);
            this.textBoxCity.Name = "textBoxCity";
            this.textBoxCity.Size = new System.Drawing.Size(190, 20);
            this.textBoxCity.TabIndex = 15;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(28, 266);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(47, 13);
            this.label10.TabIndex = 14;
            this.label10.Text = "ZipCode";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(28, 242);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(35, 13);
            this.label11.TabIndex = 13;
            this.label11.Text = "Street";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(28, 218);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(32, 13);
            this.label7.TabIndex = 12;
            this.label7.Text = "State";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(28, 194);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(71, 13);
            this.label8.TabIndex = 11;
            this.label8.Text = "NoOfTraveler";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(28, 170);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(53, 13);
            this.label9.TabIndex = 10;
            this.label9.Text = "Password";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(28, 146);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(32, 13);
            this.label4.TabIndex = 9;
            this.label4.Text = "Email";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(28, 122);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(43, 13);
            this.label5.TabIndex = 8;
            this.label5.Text = "Country";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(28, 98);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(24, 13);
            this.label6.TabIndex = 7;
            this.label6.Text = "City";
            // 
            // textBoxLastname
            // 
            this.textBoxLastname.Location = new System.Drawing.Point(104, 71);
            this.textBoxLastname.Name = "textBoxLastname";
            this.textBoxLastname.Size = new System.Drawing.Size(190, 20);
            this.textBoxLastname.TabIndex = 6;
            // 
            // textBoxMiddlename
            // 
            this.textBoxMiddlename.Location = new System.Drawing.Point(104, 47);
            this.textBoxMiddlename.Name = "textBoxMiddlename";
            this.textBoxMiddlename.Size = new System.Drawing.Size(190, 20);
            this.textBoxMiddlename.TabIndex = 5;
            // 
            // textBoxFirstname
            // 
            this.textBoxFirstname.Location = new System.Drawing.Point(104, 23);
            this.textBoxFirstname.Name = "textBoxFirstname";
            this.textBoxFirstname.Size = new System.Drawing.Size(190, 20);
            this.textBoxFirstname.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(28, 74);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 13);
            this.label3.TabIndex = 3;
            this.label3.Text = "Lastname";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(28, 50);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(64, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Middlename";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(28, 26);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(52, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "Firstname";
            // 
            // buttonUserInsert
            // 
            this.buttonUserInsert.Location = new System.Drawing.Point(511, 6);
            this.buttonUserInsert.Name = "buttonUserInsert";
            this.buttonUserInsert.Size = new System.Drawing.Size(75, 23);
            this.buttonUserInsert.TabIndex = 0;
            this.buttonUserInsert.Text = "Insert";
            this.buttonUserInsert.UseVisualStyleBackColor = true;
            this.buttonUserInsert.Click += new System.EventHandler(this.buttonUserInsert_Click);
            // 
            // Sector
            // 
            this.Sector.Location = new System.Drawing.Point(4, 22);
            this.Sector.Name = "Sector";
            this.Sector.Padding = new System.Windows.Forms.Padding(3);
            this.Sector.Size = new System.Drawing.Size(592, 392);
            this.Sector.TabIndex = 1;
            this.Sector.Text = "Sector";
            this.Sector.UseVisualStyleBackColor = true;
            // 
            // tabPageCoTraveler
            // 
            this.tabPageCoTraveler.Controls.Add(this.buttonCoTravelerInsert);
            this.tabPageCoTraveler.Controls.Add(this.textBoxCo_Relationship);
            this.tabPageCoTraveler.Controls.Add(this.textBoxCoName);
            this.tabPageCoTraveler.Controls.Add(this.textBoxUser_CO_Id);
            this.tabPageCoTraveler.Controls.Add(this.label14);
            this.tabPageCoTraveler.Controls.Add(this.label15);
            this.tabPageCoTraveler.Controls.Add(this.label16);
            this.tabPageCoTraveler.Location = new System.Drawing.Point(4, 22);
            this.tabPageCoTraveler.Name = "tabPageCoTraveler";
            this.tabPageCoTraveler.Size = new System.Drawing.Size(592, 392);
            this.tabPageCoTraveler.TabIndex = 2;
            this.tabPageCoTraveler.Text = "CoTraveler";
            this.tabPageCoTraveler.UseVisualStyleBackColor = true;
            // 
            // buttonCoTravelerInsert
            // 
            this.buttonCoTravelerInsert.Location = new System.Drawing.Point(514, 8);
            this.buttonCoTravelerInsert.Name = "buttonCoTravelerInsert";
            this.buttonCoTravelerInsert.Size = new System.Drawing.Size(75, 51);
            this.buttonCoTravelerInsert.TabIndex = 22;
            this.buttonCoTravelerInsert.Text = "Insert CoTraveler";
            this.buttonCoTravelerInsert.UseVisualStyleBackColor = true;
            this.buttonCoTravelerInsert.Click += new System.EventHandler(this.buttonCoTravelerInsert_Click);
            // 
            // textBoxCo_Relationship
            // 
            this.textBoxCo_Relationship.Location = new System.Drawing.Point(94, 63);
            this.textBoxCo_Relationship.Name = "textBoxCo_Relationship";
            this.textBoxCo_Relationship.Size = new System.Drawing.Size(190, 20);
            this.textBoxCo_Relationship.TabIndex = 21;
            // 
            // textBoxCoName
            // 
            this.textBoxCoName.Location = new System.Drawing.Point(94, 39);
            this.textBoxCoName.Name = "textBoxCoName";
            this.textBoxCoName.Size = new System.Drawing.Size(190, 20);
            this.textBoxCoName.TabIndex = 20;
            // 
            // textBoxUser_CO_Id
            // 
            this.textBoxUser_CO_Id.Location = new System.Drawing.Point(94, 15);
            this.textBoxUser_CO_Id.Name = "textBoxUser_CO_Id";
            this.textBoxUser_CO_Id.Size = new System.Drawing.Size(190, 20);
            this.textBoxUser_CO_Id.TabIndex = 19;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(18, 66);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(65, 13);
            this.label14.TabIndex = 18;
            this.label14.Text = "Relationship";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(18, 42);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(35, 13);
            this.label15.TabIndex = 17;
            this.label15.Text = "Name";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(18, 18);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(38, 13);
            this.label16.TabIndex = 16;
            this.label16.Text = "UserId";
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.button1);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(592, 392);
            this.tabPage1.TabIndex = 3;
            this.tabPage1.Text = "Test";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(242, 132);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 32);
            this.button1.TabIndex = 0;
            this.button1.Text = "Done";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(624, 442);
            this.Controls.Add(this.tabControlTest);
            this.Name = "Form1";
            this.Text = "GII Test Form";
            this.tabControlTest.ResumeLayout(false);
            this.User.ResumeLayout(false);
            this.User.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.tabPageCoTraveler.ResumeLayout(false);
            this.tabPageCoTraveler.PerformLayout();
            this.tabPage1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControlTest;
        private System.Windows.Forms.TabPage User;
        private System.Windows.Forms.TabPage Sector;
        private System.Windows.Forms.Button buttonUserInsert;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox textBoxLastname;
        private System.Windows.Forms.TextBox textBoxMiddlename;
        private System.Windows.Forms.TextBox textBoxFirstname;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox textBoxZipCode;
        private System.Windows.Forms.TextBox textBoxStreet;
        private System.Windows.Forms.TextBox textBoxState;
        private System.Windows.Forms.TextBox textBoxNoOfTraveler;
        private System.Windows.Forms.TextBox textBoxPassword;
        private System.Windows.Forms.TextBox textBoxEmail;
        private System.Windows.Forms.TextBox textBoxCountry;
        private System.Windows.Forms.TextBox textBoxCity;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox textBoxUserId;
        private System.Windows.Forms.Button buttonQuery;
        private System.Windows.Forms.Button buttonReadCoTraveler;
        private System.Windows.Forms.TabPage tabPageCoTraveler;
        private System.Windows.Forms.TextBox textBoxCo_Relationship;
        private System.Windows.Forms.TextBox textBoxCoName;
        private System.Windows.Forms.TextBox textBoxUser_CO_Id;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Button buttonCoTravelerInsert;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.Button button1;

    }
}

