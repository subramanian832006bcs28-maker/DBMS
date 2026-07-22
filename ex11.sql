db.EMPLOYEE.insertOne({
  employee_id: 1,
  employee_name: "Arun Kumar",
  employee_mobile: "9876543210",
  employee_address: "Chennai, Tamil Nadu",
  employee_role: "Manager",
  employee_status: "Active"
});
{
  acknowledged: true,
  insertedId: ObjectId('69ece128206b0518276f4664')
}
db.EMPLOYEE.insertMany([
  {
    employee_id: 2,
    employee_name: "Priya Sharma",
    employee_mobile: "9123456780",
    employee_address: "Madurai, Tamil Nadu",
    employee_role: "Developer",
    employee_status: "Active"
  },
  {
    employee_id: 3,
    employee_name: "Rahul Verma",
    employee_mobile: "9988776655",
    employee_address: "Coimbatore, Tamil Nadu",
    employee_role: "Tester",
    employee_status: "Inactive"
  },
  {
    employee_id: 4,
    employee_name: "Sneha Iyer",
    employee_mobile: "9090909090",
    employee_address: "Trichy, Tamil Nadu",
    employee_role: "HR",
    employee_status: "Active"
  }
]);
{
  acknowledged: true,
  insertedIds: {
    '0': ObjectId('69ece134206b0518276f4665'),
    '1': ObjectId('69ece134206b0518276f4666'),
    '2': ObjectId('69ece134206b0518276f4667')
  }
}
db.EMPLOYEE.updateOne(
  { employee_id: 1 },
  {
    $set: {
      employee_role: "Senior Manager",
      employee_status: "Active"
    }
  }
);
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.EMPLOYEE.updateOne(
  { employee_id: 1 },
  {
    $rename: { "employee_mobile": "employee_phone" }
  }
);
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.EMPLOYEE.updateOne(
  { employee_id: 1 },
  {
    $set: { employee_role: "Team Lead" }
  }
);
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.EMPLOYEE.replaceOne(
  { employee_id: 1 },   // filter
  {
    employee_id: 1,
    employee_name: "Arun Kumar",
    employee_mobile: "9999999999",
    employee_address: "Chennai",
    employee_role: "Director",
    employee_status: "Active"
  }
);
{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}
db.EMPLOYEE.find();
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
db.EMPLOYEE.find({ employee_id: 1 });
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
db.EMPLOYEE.find({
  $or: [
    { employee_role: "HR" },
    { employee_role: "Manager" }
  ]
});
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
db.EMPLOYEE.find({ employee_id: { $gt: 2 } });
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
db.EMPLOYEE.find({ employee_id: { $lt: 3 } });
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
db.EMPLOYEE.find({
  employee_role: { $in: ["Developer", "Tester"] }
});
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
db.EMPLOYEE.find({
  employee_role: { $nin: ["Developer", "Tester"] }
});
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
db.EMPLOYEE.find().sort({ employee_name: 1 });   
db.EMPLOYEE.find().sort({ employee_id: -1 });    
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
db.EMPLOYEE.find().sort({ employee_name: -1 });   
    
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
db.EMPLOYEE.find().sort({ employee_name: 1 });   
    
{
  _id: ObjectId('69ece128206b0518276f4664'),
  employee_id: 1,
  employee_name: 'Arun Kumar',
  employee_mobile: '9999999999',
  employee_address: 'Chennai',
  employee_role: 'Director',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4665'),
  employee_id: 2,
  employee_name: 'Priya Sharma',
  employee_mobile: '9123456780',
  employee_address: 'Madurai, Tamil Nadu',
  employee_role: 'Developer',
  employee_status: 'Active'
}
{
  _id: ObjectId('69ece134206b0518276f4666'),
  employee_id: 3,
  employee_name: 'Rahul Verma',
  employee_mobile: '9988776655',
  employee_address: 'Coimbatore, Tamil Nadu',
  employee_role: 'Tester',
  employee_status: 'Inactive'
}
{
  _id: ObjectId('69ece134206b0518276f4667'),
  employee_id: 4,
  employee_name: 'Sneha Iyer',
  employee_mobile: '9090909090',
  employee_address: 'Trichy, Tamil Nadu',
  employee_role: 'HR',
  employee_status: 'Active'
}
db.EMPLOYEE.aggregate([
  {
    $group: {
      _id: "$employee_role",
      total_employees: { $sum: 1 }
    }
  }
]);
{
  _id: 'Developer',
  total_employees: 1
}
{
  _id: 'Director',
  total_employees: 1
}
{
  _id: 'Tester',
  total_employees: 1
}
{
  _id: 'HR',
  total_employees: 1
}
db.EMPLOYEE.aggregate([
  {
    $group: {
      _id: "$employee_role",
      total: { $sum: 1 },
      max_id: { $max: "$employee_id" },
      min_id: { $min: "$employee_id" }
    }
  }
]);
{
  _id: 'HR',
  total: 1,
  max_id: 4,
  min_id: 4
}
{
  _id: 'Tester',
  total: 1,
  max_id: 3,
  min_id: 3
}
{
  _id: 'Director',
  total: 1,
  max_id: 1,
  min_id: 1
}
{
  _id: 'Developer',
  total: 1,
  max_id: 2,
  min_id: 2
}

db.EMPLOYEE.deleteOne({ employee_id: 1 });
{
  acknowledged: true,
  deletedCount: 1
}
db.EMPLOYEE.deleteMany({ employee_status: "Inactive" });
{
  acknowledged: true,
  deletedCount: 1
}
db.EMPLOYEE.deleteMany({});
{
  acknowledged: true,
  deletedCount: 2
}

